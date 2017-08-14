/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.database;

import com.mina.terminal.TMOption;
import com.mina.terminal.TMOptionAbstract;
import com.mina.terminal.Terminal;

/**
 *
 * @author hiyamamina
 */
public class ClamorTerminal extends Terminal {

    //Connection conn;
    public ClamorTerminal(TMOptionAbstract menu) {
        super(menu);
        //System.setProperty("derby.system.home", "/Users/hiyamamina/drive/javadb/");
        //callConnection("origine_mundi", "MINA", "3737", false);
        //Connection conn = getConnection("origine_mundi");
        //super.getInfo().put("conn", conn);
        getInfo().put("conn", CTUtil.getConnection());
        new CTSelectSong(true).action(getInfo());
    }

    public static void main(String[] args) {
        TMOptionAbstract menu = new TMOption("top", true)
                .addMenu('0', new CTSelectSong(false))
                .addMenu('1', new CTShowSong())
                .addMenu('2', new CTAddSong())
                .addMenu('3', new CTDeleteSong())
                .addMenu('4', new CTRecordSample())
                .addMenu('5', new CTSetMetronome());
        
        new ClamorTerminal(menu)
                .start();
    }
}
