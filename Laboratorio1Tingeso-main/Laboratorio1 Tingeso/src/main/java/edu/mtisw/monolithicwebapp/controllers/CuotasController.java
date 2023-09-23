package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.services.CuotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CuotasController{
    @Autowired
    CuotasService cuotasService;
}
