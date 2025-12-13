# Data Structure Visualizer - Project Documentation

## 1. Project Purpose
This project is an **Educational Tool** designed for Computer Science students. Its primary goal is to provide a visual representation of common data structures and their operations. By visualizing how data is inserted, deleted, and organized in memory, students can gain a deeper understanding of the underlying logic and complexity of these structures, which are fundamental to the field of Computer Science.

## 2. Module Breakdown
The project is organized into `Core` logic (data structure implementations) and `UI` (visualization logic).

### A. Linear Data Structures
These structures organize data sequentially.

#### 1. Stack (LIFO - Last In First Out)
- **Implementation**: Uses Java's built-in `java.util.Stack`.
- **Primary Logic File**: [src/ui/panels/StackVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/StackVisualizerFrame.java)
- **Functionality**: Visualizes pushing and popping elements from a stack.

#### 2. Queue (FIFO - First In First Out)
- **Implementation**: Uses Java's built-in `java.util.LinkedList` (as a Queue).
- **Primary Logic File**: [src/ui/panels/QueueVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java)
- **Functionality**: Visualizes enqueuing at the rear and dequeuing from the front.

#### 3. Singly Linked List
- **Implementation**: Uses Java's built-in `java.util.LinkedList`.
- **Primary Logic File**: [src/ui/panels/LinkedListVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/LinkedListVisualizerFrame.java)
- **Functionality**: Visualizes standard list operations. Note that while it visualizes a Singly Linked List, the underlying Java implementation is doubly linked. The visualization restricts operations to mimic singly linked behavior where appropriate.

#### 4. Array / ArrayList
- **Implementation**: Uses Java's built-in `java.util.ArrayList`.
- **Primary Logic File**: [src/ui/panels/ArrayVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/ArrayVisualizerFrame.java)
- **Functionality**: Visualizes indexed access, insertion, and deletion in a dynamic array.

#### 5. Doubly Linked List
- **Implementation**: **Custom Implementation**.
- **Primary Logic File**: [src/core/structures/DoublyLinkedList.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java)
- **Functionality**: A list where nodes point to both next and previous nodes, allowing bidirectional traversal.

#### 6. Circular Linked List
- **Implementation**: **Custom Implementation**.
- **Primary Logic File**: [src/core/structures/CircularLinkedList.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/CircularLinkedList.java)
- **Functionality**: A list where the last node points back to the head, forming a circle.

### B. Non-Linear Data Structures
These structures organize data hierarchically or interconnectedly.

#### 1. Binary Search Tree (BST)
- **Implementation**: **Custom Implementation**.
- **Primary Logic File**: [src/core/structures/BinarySearchTree.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java)
- **Functionality**: A tree where the left child is smaller and the right child is larger than the parent. Includes traversals.

#### 2. Graph
- **Implementation**: **Custom Implementation** (Adjacency List).
- **Primary Logic File**: [src/core/structures/SimpleGraph.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/SimpleGraph.java)
- **Functionality**: Represents connections between nodes (vertices) using edges. Includes traversal algorithms.

---

## 3. Key Methods & Functionality

### Linear Data Structures

#### [src/core/structures/DoublyLinkedList.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java)
| Method | Functionality |
| :--- | :--- |
| [insertFirst(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#16-27) | Creates a new node and adds it to the start of the list. Updates `head` and `prev` pointers. |
| [insertLast(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#28-39) | adds a new node to the end. Updates `tail` and `next` pointers. |
| [insertAt(int index, int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#40-63) | Inserts a node at a specific index by traversing to the position. |
| [deleteFirst()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/LinkedListVisualizerFrame.java#141-149) | Removes the head node and updates the new head's `prev` to null. |
| [deleteLast()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#76-87) | Removes the tail node and updates the new tail's `next` to null. |
| [search(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/CircularLinkedList.java#97-110) | Traverses the list to find the index of a value. |

#### [src/core/structures/CircularLinkedList.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/CircularLinkedList.java)
| Method | Functionality |
| :--- | :--- |
| [insertFirst(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#16-27) | Inserts at the head and updates the last node's `next` pointer to point to the new head. |
| [insertLast(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#28-39) | Inserts at the end and updates the new node's `next` to point to `head`. |
| [deleteFirst()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/LinkedListVisualizerFrame.java#141-149) | Removes head and updates the last node to point to the new head. |
| [deleteLast()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/DoublyLinkedList.java#76-87) | Traverses to the second-to-last node and updates its `next` to `head`. |

#### [src/ui/panels/StackVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/StackVisualizerFrame.java) (Logic Wrapper)
| Method | Functionality |
| :--- | :--- |
| [push()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/StackVisualizerFrame.java#91-107) | Pushes a value onto the `java.util.Stack`. Checks for overflow (visual limit). |
| [pop()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/StackVisualizerFrame.java#108-116) | Pops the top element. Checks for underflow. |
| [peek()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java#118-125) | Inspects the top element without removing it. |

#### [src/ui/panels/QueueVisualizerFrame.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java) (Logic Wrapper)
| Method | Functionality |
| :--- | :--- |
| [enqueue()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java#92-108) | Adds an element to the [Queue](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java#132-187) (using `offer`). Checks for capacity. |
| [dequeue()](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java#109-117) | Removes an element from the front (using `poll`). |

### Non-Linear Data Structures

#### [src/core/structures/BinarySearchTree.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java)
| Method | Functionality |
| :--- | :--- |
| [insert(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java#12-15) / [insertRec](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java#16-28) | Recursively finds the correct position and inserts a new node to maintain BST property. |
| [delete(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java#29-32) / [deleteRec](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java#33-52) | Recursively finds and removes a node. Handles 3 cases: leaf node, one child, or two children (finding inorder successor definition). |
| [search(int value)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/CircularLinkedList.java#97-110) / [searchRec](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/BinarySearchTree.java#66-73) | Recursively checks left or right subtrees to find if a value exists. |

#### [src/core/structures/SimpleGraph.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/SimpleGraph.java)
| Method | Functionality |
| :--- | :--- |
| [addNode(int node)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/SimpleGraph.java#12-19) | Adds a new vertex to the adjacency map. |
| [addEdge(int from, int to)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/SimpleGraph.java#20-30) | Adds a directed edge from `from` to [to](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/TreeAlgorithms.java#36-41) in the adjacency list. |
| [getNeighbors(int node)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/structures/SimpleGraph.java#42-45) | Returns the list of connected nodes for a given vertex. |

### Algorithms

#### [src/core/algorithms/TreeAlgorithms.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/TreeAlgorithms.java)
| Method | Functionality |
| :--- | :--- |
| [inorder(TreeNode root)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/TreeAlgorithms.java#8-13) | Returns string of values in Left-Root-Right order (Sorted for BST). |
| [preorder(TreeNode root)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/TreeAlgorithms.java#22-27) | Returns string of values in Root-Left-Right order. |
| [postorder(TreeNode root)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/TreeAlgorithms.java#36-41) | Returns string of values in Left-Right-Root order. |

#### [src/core/algorithms/GraphAlgorithms.java](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/GraphAlgorithms.java)
| Method | Functionality |
| :--- | :--- |
| [bfs(SimpleGraph graph, int start)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/GraphAlgorithms.java#8-37) | Breadth-First Search. Uses a [Queue](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/ui/panels/QueueVisualizerFrame.java#132-187) and `Set<Visited>` to explore layer by layer. |
| [dfs(SimpleGraph graph, int start)](file:///d:/Coding/Data-Structure/Data_Structure_visualizer/src/core/algorithms/GraphAlgorithms.java#38-47) | Depth-First Search. Uses recursion and `Set<Visited>` to explore as deep as possible first. |
