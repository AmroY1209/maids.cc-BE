package com.maids.cc.backend.library.controllers;

import com.maids.cc.backend.library.entities.Patron;
import com.maids.cc.backend.library.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController("PatronController")
@RequestMapping("/patrons")
public class PatronController extends BaseController<Patron, PatronService> {
    @Autowired
    @Qualifier("PatronService")  // or the name of the specific BookService bean
    private PatronService patronService;
}
