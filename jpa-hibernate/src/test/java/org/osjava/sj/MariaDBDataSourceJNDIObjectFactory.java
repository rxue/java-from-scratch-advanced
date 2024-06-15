package org.osjava.sj;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class MariaDBDataSourceJNDIObjectFactory implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable) throws Exception {
        return null;
    }
}
