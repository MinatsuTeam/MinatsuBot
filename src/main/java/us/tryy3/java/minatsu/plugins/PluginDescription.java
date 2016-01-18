package us.tryy3.java.minatsu.plugins;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class PluginDescription {
    private String name = null; // Required
    private String version = null; // Required
    private String description = null; // Optional
    private String[] authors = null; // Optional
    private String[] dependency = null; //Optional
    private String website = null; // Optional
    private String prefix = null; // Optional

    private PluginDescription(DescriptionBuilder builder) {
        this.name = builder.name;
        this.version = builder.version;
        this.description = builder.description;
        this.dependency = builder.dependency;
        this.authors = builder.authors;
        this.website = builder.website;
        this.prefix = builder.prefix;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String[] getDependency() {
        return dependency;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getWebsite() {
        return website;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String toString() {
        String s = "";
        s += (this.name != null) ? this.name + " - " : "";
        s += (this.version != null) ? this.version + " - " : "";
        s += (this.description != null) ? this.description + " - " : "";
        s += (this.authors != null) ? this.authors + " - " : "";
        s += (this.dependency != null) ? this.dependency + " - " : "";
        s += (this.website != null) ? this.website + " - " : "";
        s = s.substring(0, s.length() - 3);
        return s;
    }

    public static class DescriptionBuilder {
        private final String name;
        private final String version;
        private String description;
        private String[] authors;
        private String[] dependency;
        private String website;
        private String prefix;

        public DescriptionBuilder(String name, String version) {
            this.name = name;
            this.version = version;
        }

        public DescriptionBuilder description(String desc) {
            this.description = desc;
            return this;
        }

        public DescriptionBuilder authors(String author) {
            this.authors = new String[] {author};
            return this;
        }

        public DescriptionBuilder authors(String[] authors) {
            this.authors = authors;
            return this;
        }

        public DescriptionBuilder dependency(String depend) {
            this.dependency = new String[] {depend};
            return this;
        }

        public DescriptionBuilder dependency(String[] depend) {
            this.dependency = depend;
            return this;
        }

        public DescriptionBuilder website(String website) {
            this.website = website;
            return this;
        }

        public DescriptionBuilder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public PluginDescription build() {
            return new PluginDescription(this);
        }
    }
}
