/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

/**
 *
 * @author Mina
 */
public class OmException extends RuntimeException {
    public OmException(String str, Exception ex){
        super(str, ex);
    }
    public OmException(String str){
        super(str);
    }
    
}
