package com.cse564hateoas.appealing.representations;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class Representation {
    public static final String RELATIONS_URI = "http://relations.cse564-appeal.com/";
    public static final String CSE564APPEAL_NAMESPACE = "http://schemas.cse564-appeal.com";
    public static final String DAP_NAMESPACE = CSE564APPEAL_NAMESPACE + "/dap";
    public static final String CSE564APPEAL_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    public static final String SELF_REL_VALUE = "self";

    @XmlElement(name = "link", namespace = DAP_NAMESPACE)
    protected List<Link> links;

    protected Link getLinkByName(String uriName) {
        if (links == null) {
            return null;
        }

        for (Link l : links) {
            if (l.getRelValue().toLowerCase().equals(uriName.toLowerCase())) {
                return l;
            }
        }
        return null;
    }
}
