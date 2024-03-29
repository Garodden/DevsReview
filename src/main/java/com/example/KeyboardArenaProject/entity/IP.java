package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.IpCompositeKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "visit_ip")
@AllArgsConstructor
@NoArgsConstructor
public class IP {
    @EmbeddedId
    private IpCompositeKey compositeKey;

}
