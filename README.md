# UnboundArrayList
All the bad things you do not dare to do with an ArrayList, but you eventually do!<br>
<br>
## All jokes aside
I wanted a List implementation that...<br>
-Would not copy the entire list upon element modification.<br>
-Be synchronized on it's own, instead of me locking read and right methods.<br>
-Could, or at least try, to retrieve an element that might be removed by another task.<br>
-Continue with iteration even if another task modified the list.<br>
<br>
### What is done
-Extends ArrayList, so copy occurs only when the list is full.<br>
-Synchronized read and write methods via the list object itself.<br>
-Return null elements instead of throwing IndexOutOfBoundsException using try.<br>
-Index checking for methods that use indexes to modify data.<br>
-Iterator that also returns null for nonexistent elements.<br>
-Addition of synchronized addIfAbsent method.<br>
