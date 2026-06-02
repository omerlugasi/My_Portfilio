# Treap-Based Stock Manager

Stock management system implemented using randomized balanced binary search trees (Treaps).

The system maintains:
- A stock index by stock ID
- A price index for efficient range queries

Main components:
- `Treap` — randomized balanced BST implementation
- `TreapMap` — generic key-value map built on top of Treaps
- `StockManager` — high-level stock management logic
- `PriceIdKey` — composite key for price indexing

Main supported operations:
- Stock insertion and deletion
- Stock price updates
- Price range counting
- Ordered stock retrieval within price ranges

The implementation focuses on efficient dynamic updates while maintaining ordered indexing performance.