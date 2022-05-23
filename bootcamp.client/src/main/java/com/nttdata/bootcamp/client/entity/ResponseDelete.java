package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDelete implements Serializable {
private static final long serialVersionUID = 2513677250230235247L;
private String response;
}
