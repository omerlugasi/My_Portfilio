# 2-3 Tree Based Stock Manager

Stock management system implemented using a custom generic 2-3 Tree data structure.

The implementation includes:
- Generic key abstraction
- Sentinel-based tree boundaries
- Rank calculation
- Successor and predecessor operations
- Linked leaves for efficient traversal
- Timestamp-based stock update history

Main components:
- `Tree2_3` — custom 2-3 Tree implementation
- `TNode` — tree node implementation
- `StockManager` — stock management logic
- `StringKey`, `FloatKey`, `LongKey` — generic key types

Main supported operations:
- Dynamic stock insertion and deletion
- Timestamped stock updates
- Range queries
- Ordered stock traversal
- Rank-based counting operations

The project focuses on implementing balanced search trees and efficient ordered data retrieval from scratch.