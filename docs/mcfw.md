# MCFW

Minecraft Firewall packet filter.

Usage:\
    - mcfw status (show ON/OFF)\
    - mcfw enable (enable MCFW)\
    - mcfw disable (disable MCFW)\
    - mcfw quiet-disable (disable without sending delayed packets)\
    - mcfw list (list packet filter)\
    - mcfw delayed (show delayed packets count)\
    - mcfw clear (clear packet filter)\
    - mcfw clear-delayed (clear delayed packets queue)\
    - mcfw block &lt;packet name&gt; (block outgoing packet)\
    - mcfw delay &lt;packet name&gt; (delay (until disable) outgoing packet)\
    - mcfw remove &lt;packet name&gt; (remove from packet filter)

Packet Names:\
TeleportConfirm\
QueryBlockNbt\
ChatMessage\
ClientStatus\
ClientOptions\
RequestCommandCompletions\
ButtonClick\
ClickSlot\
CloseHandledScreen\
CustomPayload\
BookUpdate\
QueryEntityNbt\
PlayerInteractEntity\
PlayerInteractEntity$InteractHandler\
PlayerInteractEntity$InteractAtHandler\
KeepAlive\
PlayerMove$PositionAndOnGround\
PlayerMove$Full\
PlayerMove$LookAndOnGround\
PlayerMove$OnGroundOnly\
VehicleMove\
BoatPaddleState\
PickFromInventory\
CraftRequest\
UpdatePlayerAbilities\
PlayerAction\
ClientCommand\
PlayerInput\
RecipeBookData\
RenameItem\
ResourcePackStatus\
AdvancementTab\
SelectMerchantTrade\
UpdateBeacon\
UpdateSelectedSlot\
UpdateCommandBlock\
UpdateCommandBlockMinecart\
CreativeInventoryAction\
UpdateStructureBlock\
UpdateSign\
HandSwing\
SpectatorTeleport\
PlayerInteractBlock\
PlayerInteractItem\
Handshake\
LoginQueryResponse\
LoginHello\
LoginKey\
QueryPing\
QueryRequest\
UpdateJigsaw\
UpdateDifficulty\
UpdateDifficultyLock\
JigsawGenerating\
RecipeCategoryOptions\
CommonPong\
CommandExecution\
MessageAcknowledgment\
PlayerSession\
AcknowledgeChunks\
AcknowledgeReconfiguration\
EnterConfiguration\
Ready

---