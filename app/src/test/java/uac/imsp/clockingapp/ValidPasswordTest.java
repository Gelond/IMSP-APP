package uac.imsp.clockingapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import entity.Employee;


public class ValidPasswordTest {
    @Test
    public void  test(){

        Employee employee=new Employee(20);
        employee.setPassword("");
        assertTrue(employee.hasInvalidPassword());
       //pas de majuscule
       employee.setPassword("hfjgg24");
        assertTrue(employee.hasInvalidPassword());
        //valide
employee.setPassword("dUK6%i");
        assertFalse(employee.hasInvalidPassword());
        //pas de caractère spécial
        employee.setPassword("AAge56");
        assertTrue(employee.hasInvalidPassword());

       // valide
        employee.setPassword("A%566v");
        assertFalse(employee.hasInvalidPassword());
        //valide
        employee.setPassword("/55Abc");
        assertFalse(employee.hasInvalidPassword());

        //valide
        employee.setPassword("Abcd*-03");
        assertFalse(employee.hasInvalidPassword());
        //valide
        employee.setPassword("06+/Ab9");
        assertFalse(employee.hasInvalidPassword());

        //pas de caractère spécial
        employee.setPassword("023Abjfjfkg4");
        assertTrue(employee.hasInvalidPassword());






    }

}


