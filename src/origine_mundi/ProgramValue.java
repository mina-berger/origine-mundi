/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

/**
 *
 * @author mina
 */
public class ProgramValue {
    private final Integer bank_m;
    private final Integer bank_l;
    private final int program;
    public ProgramValue(int program){
        this.bank_m = null;
        this.bank_l = null;
        this.program = program;
    }
    public ProgramValue(int bank_m, int bank_l, int program){
        this.bank_m = bank_m;
        this.bank_l = bank_l;
        this.program = program;
    }
    public boolean hasBank(){
        return bank_m != null && bank_l != null;
    }
    public int getBank_m() {
        return bank_m;
    }

    public int getBank_l() {
        return bank_l;
    }

    public int getProgram() {
        return program;
    }
}
