package jp.ac.dendai.im.cps.footopic;

/**
 * Created by naoya on 15/12/12.
 */
public enum HttpType {
    Post ( 1 ),
    Get ( 2 );

    private final int id ;

    private HttpType ( final int id ) {
        this . id = id ;
    }

    public int getId () {
        return id ;
    }
}
