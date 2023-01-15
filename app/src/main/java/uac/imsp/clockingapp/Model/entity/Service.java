package entity;
/** Represents a service.
 *<br/>
 * Proposes day attributes and exposes  methods to manage services.<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class Service implements IService {

    //attributes
    /**Represents the id of the service **/
    protected int Id;
    /**Represents the name of the service **/
    protected String Name;



    //constructeurs

    //un service non existant
    /** A constructor of service: it creates a new service
     *  with the given  service name
     * @param name is the name of the service
     */
    public Service(String name){
        Name = name;
    }
    //un service existant
    /** A constructor to use an existing service: it creates a  service
     *  with the given  service id
     * @param id is the service id
     */
    public Service(int id){
        Id=id;
    }
    ///getters
    /**@return  the id of the service**/
    public int getId(){
        return Id;
    }
    /**@return  the name of the service**/
    public String getName(){
        return Name;
    }

    //setters

    /**Sets id to the service
     * @param id is the service id**/
    public void setId(int id){
        Id = id;
    }
    /**Sets id to the service
     * @param name is the name of the service**/
    public void setName(String name){
        Name = name;
    }


}
