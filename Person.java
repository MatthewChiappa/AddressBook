public class Person {
  
  String name, address, number;
  
  public Person(String name, String address, String number) {
   
    this.name = name;
    this.address = address;
    this.number = number;
    
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public void setNumber(String number) {
    this.number = number;
  }
  
  public String getName() {
    return name;
  }
  
  public String getAddress() {
    return address;
  }
  
  public String getNumber() {
    return number;
  }
  
}