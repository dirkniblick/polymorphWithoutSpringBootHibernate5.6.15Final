package com.example.hibernatepolymorph.entity;


import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.MetaValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Entity
@Table(name = "property_repository")
public class PropertyRepository {

    @Id
    private Long id;

    @ManyToAny(metaColumn = @Column( name = "property_type" ))
    @AnyMetaDef( metaType = "string", idType = "long",
            metaValues = {
                    @MetaValue(value = "S", targetEntity = StringProperty.class),
                    @MetaValue(value = "I", targetEntity = IntegerProperty.class)
            }
    )
    @Cascade( { org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name = "repository_properties",
            joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property<?>> properties = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Property<?>> getProperties() {
        return properties;
    }

    public void setProperties(List<Property<?>> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PropertyRepository.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("properties=" + properties.stream().map(Property::getName).collect(Collectors.joining(",")))
                .toString();
    }
}