package IR;

public enum REGISTERS {
    //some of these nee to be appended with a number, some don't. those with one letter need a num appended to it
    ZERO("zero"),
    VALUE("$v"),
    ARGUMENT("$a"),
    STACK_POINTER("$sp"),
    FRAME_POINTER("$fp"),
    RETURN_ADDRESS("$ra");

    public String name;

    REGISTERS(String name) {
        this.name = name;
    }
}
