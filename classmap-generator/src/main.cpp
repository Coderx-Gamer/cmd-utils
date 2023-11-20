#include <iostream>
#include <map>
#include <string>
#include <sstream>
#include <curlpp/cURLpp.hpp>
#include <curlpp/Easy.hpp>
#include <curlpp/Options.hpp>

using std::cout;
using std::endl;
using std::map;
using std::string;
using std::stringstream;

using namespace curlpp;
using namespace curlpp::options;

void issue(string problem)
{
     cout << "Error: " << problem << '\n';
     exit(1);
}

string replace_all(string subject, const string &search, const string &replace)
{
     size_t pos = 0;
     while ((pos = subject.find(search, pos)) != string::npos)
     {
          subject.replace(pos, search.length(), replace);
          pos += replace.length();
     }
     return subject;
}

string http_get(const string &url) {
     stringstream response_stream;

     try
     {
          Cleanup cleanup;
          Easy request;

          request.setOpt(new Url(url));
          request.setOpt(new WriteStream(&response_stream));

          request.perform();
     }
     catch (RuntimeError &e)
     {
          cout << "curlpp runtime error: " << e.what() << endl;
          exit(1);
     }
     catch (LogicError &e)
     {
          cout << "curlpp logic error: " << e.what() << endl;
          exit(1);
     }

     return response_stream.str();
}

string slice_str(const string &str, int start, int end)
{
     end += start;
     string new_str;
     int i;
     for (i = 0; i < (int) str.length(); ++i)
     {
          if (!(i > start && i < end))
               new_str += str[i];
     }
     return new_str;
}

string replace_char(string str, char c, char nc)
{
     size_t i;
     for (i = 0; i < str.length(); ++i)
     {
          if (str[i] == c)
          {
               str[i] = nc;
               break;
          }
     }
     return str;
}

string format_html_line(const string &line)
{
     return line.substr(0, line.find_first_of('<'));
}

string trim_packet(const string &packet)
{
     string modified = replace_char(packet, '.', '$');
     return slice_str(modified, modified.rfind("C2SPacket") - 1, 10);
}

string intermediate_name(const string &packet, const string &package, const string &version)
{
     string res = http_get("https://maven.fabricmc.net/docs/" + version + "/net/minecraft/network/packet/c2s/" + package + "/" + packet + ".html");
     size_t location = res.rfind("<td>intermediary</td>\n<td><span class=\"copyable\"><code>net/minecraft/");
     if (location == string::npos)
          issue("Failed To Scrape Intermediate Class Name (Packet Name: " + packet + ").");
     string name = res.substr(location + 69, 22);
     return name.substr(0, name.find_first_of('<'));
}

void get_packets(map<string, string> &packet_map, const string &package, const string &version)
{
     cout << "Fetching classes in net.minecraft.network.packet.c2s." << package << ".\n";
     string res = replace_all(http_get("https://maven.fabricmc.net/docs/" + version + "/net/minecraft/network/packet/c2s/" + package  + "/package-summary.html"), "\"class in net.minecraft.network.packet.c2s." + package + "\">", "\n");
     string line;
     size_t i;
     for (i = 0; i < res.length(); ++i)
     {
          if (res[i] == '\n')
          {
               if (line[0] != '<' && line[0] != 'l' && line[0] != ' ')
               {
                    line = format_html_line(line);
                    if (line.rfind("C2SPacket") != string::npos && line != "PlayerMoveC2SPacket")
                         packet_map[intermediate_name(line, package, version)] = trim_packet(line);
               }
               line = {};
               continue;
          }

          line += res[i];
     }
}

map<string, string> get_all_packets(const string &version)
{
     map<string, string> packet_map;
     get_packets(packet_map, "play", version);
     get_packets(packet_map, "common", version);
     get_packets(packet_map, "config", version);
     get_packets(packet_map, "query", version);
     get_packets(packet_map, "login", version);
     get_packets(packet_map, "handshake", version);
     return packet_map;
}

int main(int argc, char **argv)
{
     if (argc != 2)
          issue("Invalid Arguments, usage: 'cmgen <yarn version>'.");

     string version = string(argv[1]);

     cout << "This may take a few minutes ...\n";

     map<string, string> packets = get_all_packets(version);

     cout << "\nJava Code:\n\nimport java.util.HashMap;\nimport java.util.Map;\n\npublic class ClassMap {\n     public static final Map<String, String> PACKET_MAP = new HashMap<>();\n\n     static {\n";

     for (const auto &[key, value] : packets)
     {
          cout << "          PACKET_MAP.put(\"" << key << "\", \"" << value << "\");\n";

     }
     cout << "     }\n}\n\nDone.\n";

     return 0;
}
