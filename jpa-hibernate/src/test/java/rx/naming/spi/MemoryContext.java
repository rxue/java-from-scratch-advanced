package rx.naming.spi;

import javax.naming.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

class MemoryContext implements Context {
    private Map<String,Object> lookupMap = new HashMap<>();
    @Override
    public Object lookup(Name name) throws NamingException {
        final OneLevelName oneLevelName = (OneLevelName) name;
        return lookupMap.get(oneLevelName.value());
    }

    @Override
    public Object lookup(String s) throws NamingException {
        return null;
    }

    @Override
    public void bind(Name name, Object o) throws NamingException {
        System.out.println("bind by Name");
    }

    @Override
    public void bind(String s, Object o) throws NamingException {
        lookupMap.put(s, o);
    }

    @Override
    public void rebind(Name name, Object o) throws NamingException {
        System.out.println("rebind by Name");
    }

    @Override
    public void rebind(String s, Object o) throws NamingException {
        System.out.println("rebind by String");

    }

    @Override
    public void unbind(Name name) throws NamingException {

    }

    @Override
    public void unbind(String s) throws NamingException {

    }

    @Override
    public void rename(Name name, Name name1) throws NamingException {

    }

    @Override
    public void rename(String s, String s1) throws NamingException {

    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String s) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String s) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String s) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String s) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String s) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(String s) throws NamingException {
        return  OneLevelName::new;
    }

    @Override
    public Name composeName(Name name, Name name1) throws NamingException {
        return null;
    }

    @Override
    public String composeName(String s, String s1) throws NamingException {
        return "";
    }

    @Override
    public Object addToEnvironment(String s, Object o) throws NamingException {
        return null;
    }

    @Override
    public Object removeFromEnvironment(String s) throws NamingException {
        return null;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    @Override
    public void close() throws NamingException {

    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return "";
    }

    private static class OneLevelName implements Name {
        private final String value;
        public OneLevelName(String value) {
            this.value = value;
        }

        @Override
        public Object clone() {
            return null;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<String> getAll() {
            return null;
        }

        @Override
        public String get(int i) {
            return "";
        }

        @Override
        public Name getPrefix(int i) {
            return null;
        }

        @Override
        public Name getSuffix(int i) {
            return null;
        }

        @Override
        public boolean startsWith(Name name) {
            return false;
        }

        @Override
        public boolean endsWith(Name name) {
            return false;
        }

        @Override
        public Name addAll(Name name) throws InvalidNameException {
            return null;
        }

        @Override
        public Name addAll(int i, Name name) throws InvalidNameException {
            return null;
        }

        @Override
        public Name add(String s) throws InvalidNameException {
            return null;
        }

        @Override
        public Name add(int i, String s) throws InvalidNameException {
            return null;
        }

        @Override
        public Object remove(int i) throws InvalidNameException {
            return null;
        }
        String value() {
            return value;
        }
    }
}
