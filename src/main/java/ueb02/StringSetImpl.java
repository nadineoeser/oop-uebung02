package ueb02;

import java.util.NoSuchElementException;

public class StringSetImpl implements StringSet {


    class Element {

       String value;
       Element left, right;

       public Element(String value, Element left, Element right) {
           this.value = value;
           this.left = left;
           this.right = right;
       }



       public boolean add(String s) {
           if(s.compareTo(value) < 0){
               if (left != null)
                   return left.add(s);
               left = new Element(s, null, null);
               return true;
           }

           if (s.compareTo(value) > 0){
               if (right != null)
                   return right.add(s);
               right = new Element(s, null, null);
               return true;
           }

           return false;
       }


       public boolean contains(String s) {
           if (s.compareTo(value) == 0)
               return true;

           if (s.compareTo(value) < 0){
               if (left != null)
                   return left.contains(s);
           }

           if (s.compareTo(value) > 0){
               if (right != null)
                   return right.contains(s);
           }
           return false;
       }

       public int size() {
           int s = 1;

           if (left != null)
               s += left.size();

           if (right != null)
               s += right.size();

           return s;
       }

       Element checkSubTrees(String s){

           Element element = checkElement(this.left,s);
           if (element == null){
               element = checkElement(this.right,s);
           }

           if (element != null){
               if (element.right != null){
                   Element prev = element;
                   Element it = element.right;
                   while (it.left != null){
                       prev = it;
                       it = it.left;
                   }
                   prev.left = null;
                   it.left = element.left;
                   it.right = element.right;
                   this.right = it;
               }
               this.left = element.left;
           }
           return element;
       }

       @Override
       public String toString(){
           return "Element{" +
                   "value='" + value + '\'' +
                   ", left=" + left +
                   ", right=" + right +
                   '}';
       }

       private Element checkElement(Element element, String s){
           if (element == null)
               return null;

           if (element.value.compareTo(s) == 0){
               return element;
           }

           return element.checkSubTrees(s);
       }
   }

   private Element root;

    @Override
    public boolean add(String s) {
        // check if root ist null
        if (root == null){
            root = new Element(s, null, null);
            return true;
        }
        return root.add(s);
    }

    /**
     * Check if set contains value s.
     *
     * @param s
     * @return
     */
    @Override
    public boolean contains(String s) {
        if (root == null)
            return false;

        return root.contains(s);
    }

    /**
     * Removes s from set.
     *
     * @param s
     * @return
     */
    @Override
    public String remove(String s) {
        if (root == null){
            throw new NoSuchElementException();
        }

        Element result = root.checkSubTrees(s);

        if (result == null)
            throw new NoSuchElementException();

        return result.value;
    }

    /**
     * Return numer of elements in set.
     *
     * @return
     */
    @Override
    public int size() {
        if (root == null)
            return 0;

        return root.size();
    }

    @Override
    public String toString(){
        return "StringSetImpl{" +
                "root=" + root +
                '}';
    }
}
