public class LibrarySystem {
 public static void main(String[] args) {
   Author a1 = new Author("Twinity Twint", null);
   Author a2 = new Author("Lil Frank", null);
   Author a3 = new Author("Kobe Bar Nuts", null);
   
   Book b1 = new Book("Intro to OOP", a1, "October 11, 1997");
   Book b2 = new Book("Data Communications", a1, "March 12, 2010");
   Book b3 = new Book("Web Engineering", a1, "July, 28, 2015");
   
   Book b4 = new Book("Rap God", a2, "June 20, 1998");
   Book b5 = new Book("Without Me", a2, "May 24, 2004");
   Book b6 = new Book("Offended", a2, "February 1, 1999");
   
   Book b7 = new Book("Black Mamba", a3, "January 31, 1970");
   Book b8 = new Book("Clutch", a3, "December 19, 2005");
   Book b9 = new Book("Dear Basketball", a3, "April 16, 2016");
   
   IBookList mt = new EmptyBookList();
   
   IBookList list1 = new ConsBookList(b1, mt);
   IBookList list2 = new ConsBookList(b2, list1);
   IBookList list3 = new ConsBookList(b3, list2);
   
   IBookList list4 = new ConsBookList(b4, mt);
   IBookList list5 = new ConsBookList(b5, list4);
   IBookList list6 = new ConsBookList(b6, list5);
   
   IBookList list7 = new ConsBookList(b7, mt);
   IBookList list8 = new ConsBookList(b8, list7);
   IBookList list9 = new ConsBookList(b9, list8);
   
   a1.booksWritten = list3;
   a2.booksWritten = list6;
   a3.booksWritten = list9;
   
   IBookList main1 = new ConsBookList(b8, mt);
   IBookList main2 = new ConsBookList(b2, main1);
   IBookList main3 = new ConsBookList(b5, main2);
   IBookList main4 = new ConsBookList(b9, main3);
   IBookList main5 = new ConsBookList(b7, main4);
   IBookList main6 = new ConsBookList(b3, main5);
   IBookList main7 = new ConsBookList(b1, main6);
   IBookList main8 = new ConsBookList(b4, main7);
   IBookList main9 = new ConsBookList(b6, main8);
   
   System.out.println();
   System.out.println("List of Books Available:");
   main9.displayBooks();
   
   System.out.println(main9.getAuthorOfBook());
   
   System.out.println("---------- Sorted By Title ----------");
   main9.sortByTitle().displayBooks();
   
   System.out.println("---------- Sorted By Author ----------");
   main9.sortByAuthor().displayBooks();
   
   System.out.println("---------- Books of Twinity Twint ----------");
   main9.filterByAuthor("Twinity Twint").displayBooks();
   
   System.out.println("---------- Books of Lil Frank ----------");
   main9.filterByAuthor("Lil Frank").displayBooks();
   
   System.out.println("---------- Books of Kobe Bar Nuts ----------");
   main9.filterByAuthor("Kobe Bar Nuts").displayBooks();
   
 }
}

interface ICheckBook {
 public boolean check(Book book);
}

interface IBookList {
 IBookList displayBooks();
 
 String getAuthorOfBook();
 String getTitleOfBook();

 IBookList sortByTitle();
 IBookList sortByAuthor();
 IBookList insertBookTitle(Book b);
 IBookList insertBookAuthor(Book b);

 IBookList filterByAuthor(String author);
}

class Book {
 String title;
 Author author;
 String datePublished;

 Book(String title, Author author, String datePublished) {
  this.title = title;
  this.author = author;
  this.datePublished = datePublished;
 }

 public String getAuthor() {
  return this.author.getName();
 }

 public String getBookTitle() {
  return this.title;
 }
}

class Author {
 String name;
 IBookList booksWritten;

 Author(String name, IBookList booksWritten) {
  this.name = name;
  this.booksWritten = booksWritten;
 }

 public String getName() {
  return this.name;
 }

  public IBookList written() {
  return this.booksWritten;
 }
}

class EmptyBookList implements IBookList {
 public EmptyBookList() {}

 public IBookList sortByTitle() {
  return this;
 }

 public IBookList sortByAuthor() {
  return this;
 }

 public String getTitleOfBook() {
  return "";
 }

 public String getAuthorOfBook() {
  return "";
 }

 public IBookList displayBooks() {
  return null;
 }

 public IBookList insertBookTitle(Book b) {
  return new ConsBookList(b, this);
 }

 public IBookList insertBookAuthor(Book b) {
  return new ConsBookList(b, this);
 }

 public IBookList filterByAuthor(String author) {
  return this;
 }
}

class ConsBookList implements IBookList {
 Book first;
 IBookList rest;

 ConsBookList(Book first, IBookList rest) {
  this.first = first;
  this.rest = rest;
 }

 public IBookList displayBooks() {
  System.out.println(first.getBookTitle());
  return rest.displayBooks();
 }

 public String getTitleOfBook() {
  return first.getBookTitle();
 }

 public IBookList sortByTitle() {
  return this.rest.sortByTitle().insertBookTitle(this.first);
 }

 public IBookList sortByAuthor() {
  return this.rest.sortByAuthor().insertBookAuthor(this.first);
 }

 public boolean bookComesBefore(Book that) {
  if((this.getTitleOfBook().compareTo(that.getBookTitle())) <= 0) {
   return true;
  } else {
   return false;
  }
 }

 public boolean authorComesBefore(Book that) {
  if((this.getAuthorOfBook().compareTo(that.getAuthor())) <= 0) {
   return true;
  } else {
   return false;
  }
 }

 public String getAuthorOfBook() {
  return first.getAuthor();
 }

 public IBookList insertBookTitle(Book b) {
  if(this.bookComesBefore(b)) {
   return new ConsBookList(this.first, this.rest.insertBookTitle(b));
  } else {
   return new ConsBookList(b, this);
  }
 }

 public IBookList insertBookAuthor(Book b) {
  if(this.authorComesBefore(b)) {
   return new ConsBookList(this.first, this.rest.insertBookAuthor(b));
  } else {
   return new ConsBookList(b, this);
  }
 }

 public IBookList filterByAuthor(String author) {
  if(this.first.getAuthor().equals(author)) {
   return new ConsBookList(this.first, rest.filterByAuthor(author));
  } else {
   return rest.filterByAuthor(author);
  }
 }
}