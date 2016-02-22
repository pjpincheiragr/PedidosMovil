package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 09/11/2015.
 */

import java.io.Serializable;
import java.util.List;

public class Actions implements Serializable{

    List<IsisService> links;
    Extensions extensions;
    String title;
    String serviceId;
    Members members;

    public List<IsisService> getLinks() {
        return links;
    }

    public void setLinks(List<IsisService> links) {
        this.links = links;
    }

    public Extensions getExtensions() {
        return extensions;
    }

    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    public class Extensions{

        String oid;
        Boolean isService;
        Boolean isPersistent;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public Boolean getIsService() {
            return isService;
        }

        public void setIsService(Boolean isService) {
            this.isService = isService;
        }

        public Boolean getIsPersistent() {
            return isPersistent;
        }

        public void setIsPersistent(Boolean isPersistent) {
            this.isPersistent = isPersistent;
        }
    }

    public class Members {
        IsisAction listAll;
        IsisAction create;

        public IsisAction getListAll() {
            return listAll;
        }

        public void setListAll(IsisAction listAll) {
            this.listAll = listAll;
        }

        public IsisAction getCreate() {
            return create;
        }

        public void setCreate(IsisAction create) {
            this.create = create;
        }
    }
}
