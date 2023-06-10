package edu.school21.Annotation.Model;

import edu.school21.Annotation.Annotations.HtmlForm;
import edu.school21.Annotation.Annotations.HtmlInput;

@HtmlForm(fileName = "car_form.html", action = "/cars", method = "post")
public class CarForm {
    @HtmlInput(type = "text", name = "Tesla", placeholder = "Enter the model of the car")
    private String model;
    @HtmlInput(type = "text", name = "Armen", placeholder = "Enter the name of the owner")
    private String owner;
    @HtmlInput(type = "mileage", name = "120_000", placeholder = "Enter the mileage of the car")
    private String mileage;
}
