package com.example.Payroll;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.aspectj.weaver.ast.Or;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        EntityModel<Order> orderModel =  EntityModel.of(order,
                linkTo(methodOn(EmployeeController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("orders"));

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }

        return orderModel;
    }
}
