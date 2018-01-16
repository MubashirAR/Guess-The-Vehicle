package com.xmlscanner.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.sql.ordering.antlr.GeneratedOrderByLexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.springframework.mock.web.MockMultipartFile;

import com.xmlscanner.dao.UploadsDAO;
import com.xmlscanner.dao.VehicleDetailsDAO;
import com.xmlscanner.dao.WheelDAO;
import com.xmlscanner.model.Uploads;
import com.xmlscanner.model.VehicleDetails;
import com.xmlscanner.model.Wheel;

@CrossOrigin
@RestController
public class XMLService {
	
	@Autowired
	VehicleDetails vehicleDetails;
	
	@Autowired
	VehicleDetailsDAO vehicleDetailsDAO;
	
	@Autowired
	Wheel wheel;
	
	@Autowired
	WheelDAO wheelDAO;
	
	@Autowired
	UploadsDAO uploadsDAO;
	
	@Autowired
	Uploads uploads;
	
	@GetMapping("/hello")
	public String sayHello()
	{
		return "  Hello from User rest service";
	}
	
	List<Wheel> wheels = new ArrayList<>();
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public List<VehicleDetails> upload(@RequestParam("file") MultipartFile file ) throws IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        byte[] bytes;
        List<VehicleDetails> vehiclelist = new ArrayList();

        if (!file.isEmpty()) {
        	            
             
             try {
            	 DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            	 //Document doc = documentBuilder.parse(convFile);
            	 File convFile = new File(file.getOriginalFilename());
            	    convFile.createNewFile(); 
            	    FileOutputStream fos = new FileOutputStream(convFile); 
            	    fos.write(file.getBytes());
            	    fos.close(); 
				Document doc = documentBuilder.parse(convFile);
				
				
				Long ID = vehicleDetailsDAO.getMaxXMLString();
				NodeList vehicles = doc.getElementsByTagName("vehicles");
				String str = convertDocumentToString(doc);
				// LOOP THROUGH VEHICLES
//				for (int i = 0; i < vehicles.getLength(); i++) {
					VehicleDetails thisVehicle = new VehicleDetails();
					uploads.setXmlfile(str.toCharArray());
					//thisVehicle.setXMLFile(str.toCharArray());
					thisVehicle.setXMLString(ID);
					Node vehiclesNode = vehicles.item(0);
					NodeList vehicle =vehiclesNode.getChildNodes();
					
					// LOOP THROUGH TAGS WITHIN ONE VEHICLE
					for (int j = 0; j < vehicle.getLength(); j++) {
						
					if (vehicle.item(j).getNodeName().trim().equalsIgnoreCase("vehicle")) {
						
						int NumberOfWheels = 0;
						
							if (vehicle.item(j).getNodeType() == Node.ELEMENT_NODE) {
								thisVehicle = new VehicleDetails();
								
								//
								Element e = (Element) (Node) vehicle.item(j);

								NodeList newList = e.getChildNodes();

								
								for (int l = 0; l < newList.getLength(); l++) {

									Node elements = newList.item(l);
									if (elements.getNodeType()==Node.ELEMENT_NODE) {
										thisVehicle = vehicleMethod(elements, thisVehicle, NumberOfWheels);
										System.out.println(thisVehicle);
									}
									
								}
								if (thisVehicle.getFrame()!=null && thisVehicle.getPowerTrain()!=null && thisVehicle.getId()==0) {
									
									//thisVehicle.setXMLString(ID);
									
									//thisVehicle.setXMLFile(str.toCharArray());
									vehiclelist.add(thisVehicle);
								}

								System.out.println(thisVehicle);
							}
							

						 
					}
					}
					
					
					
//				}
				
				uploads.setXmlfile(str.toCharArray());
				uploads = uploadsDAO.save(uploads);
				
				
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("Invalid file!");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
        }// file !empty
        
        
// NOW LETS CHECK WHAT TYPE OF VEHICLE IT IS
        for(VehicleDetails vehicle : vehiclelist) {
        	vehicle.setFileID(uploads.getId());
			
        	if (vehicle.getFrame().trim().equalsIgnoreCase("metal")) {
				//It is a Bicycle, Motorcycle or a car
        		if (vehicle.getPowerTrain().trim().equalsIgnoreCase("Internal Combustion")) {
        			//Car or Motorcycle
					if (vehicle.getNumberOfWheels().equals("2")) {
						vehicle.setType("Motorcycle");
						System.out.println("This is a motorcycle");
						vehicle = vehicleDetailsDAO.save(vehicle);
					} else {
						//Car
						vehicle.setType("Car");
						vehicle = vehicleDetailsDAO.save(vehicle);

					}
				}else {
					//Bicycle
					vehicle.setType("Bicycle");
					vehicle = vehicleDetailsDAO.save(vehicle);
				}
			}else {
				//Big Wheel or HandGlider
				if (vehicle.getPowerTrain().trim().equalsIgnoreCase("Human")) {
					//Big Wheel
					vehicle.setType("Big Wheel");
					vehicle =vehicleDetailsDAO.save(vehicle);
				} else {
					//Hand Glider
					vehicle.setType("Hand Glider");
					vehicle =vehicleDetailsDAO.save(vehicle);

				}
			} 
        	
        	if (vehicle!=null) {
        		for(Wheel wheel : wheels)
        			wheel.setVehicle_id(Long.toString(vehicle.getId()));
        		wheelDAO.save(wheel);
			}
        	
        }
        
        
        
        
        return vehiclelist;
        
    }// Upload Method Ends
	
	private VehicleDetails vehicleMethod(Node elements, VehicleDetails thisVehicle, int NumberOfWheels)
	{
		if(elements.getNodeType() == Node.ELEMENT_NODE) {
			 
			System.out.println(elements.getNodeName());
			//Is it the ID??
			switch (elements.getNodeName().trim().toLowerCase()) {
			case "id":
				System.out.println(elements.getTextContent());
				
				break;
			case "frame":
				NodeList frameMaterialList =elements.getChildNodes();
				for (int m = 0; m < frameMaterialList.getLength(); m++) {
					Node frameMaterial = frameMaterialList.item(m);
					if (frameMaterial.getNodeType()==Node.ELEMENT_NODE && frameMaterial.getNodeName().trim().equalsIgnoreCase("material")) {
						thisVehicle.setFrame(frameMaterial.getTextContent());
					}
				}
				break;
			case "wheels":
				//Get the number of wheels
				Wheel wheelObj = new Wheel();
				System.out.println(elements.getNodeName());
				NodeList wheelList = elements.getChildNodes();
				for (int m = 0; m < wheelList.getLength(); m++) {
					Node wheel = wheelList.item(m);
					System.out.println(wheel.getNodeName());
					if (wheel.getNodeName().trim().equalsIgnoreCase("wheel")) {
						NumberOfWheels++;
						for (int i = 0; i < wheelList.getLength(); i++) {
							if (wheelList.item(i).getNodeType() == Node.ELEMENT_NODE) {
								if (wheelList.item(i).getNodeName().equalsIgnoreCase("material")) {
									wheelObj.setMaterial(wheelList.item(i).getTextContent());
								}
								else if (wheelList.item(i).getNodeName().equalsIgnoreCase("plastic")) {
									wheelObj.setPosition(wheelList.item(i).getTextContent());
								}
							}
						}
					}
				}
				wheels.add(wheelObj);
				
				
				thisVehicle.setNumberOfWheels(Integer.toString(NumberOfWheels));
				break;
			case "powertrain":
				NodeList powertrainDetailsList =elements.getChildNodes();
				for (int m = 0; m < powertrainDetailsList.getLength(); m++) {
					Node powertrainType = powertrainDetailsList.item(m);
					if (powertrainType.getNodeType()==Node.ELEMENT_NODE && (powertrainType.getNodeName().trim().equalsIgnoreCase("human") || powertrainType.getNodeName().trim().equalsIgnoreCase("Internal Combustion") || powertrainType.getNodeName().trim().equalsIgnoreCase("Bernoulli"))) {
						thisVehicle.setPowerTrain((powertrainType.getNodeName()));
					}
				}
				break;

			default:
				break;
			}
			
			
			
			System.out.println(thisVehicle);
		}
		return thisVehicle;
	}
	
	
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("id") String id,HttpServletResponse response ) throws IOException {
		uploads = uploadsDAO.get(id);
		String uploadString = new String(uploads.getXmlfile());
		//StringReader stringReader = new StringReader(vehicleString);
		DocumentBuilder builder;  
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 Document doc;
        try 
        {  
            builder = factory.newDocumentBuilder();  
            doc = builder.parse( new InputSource( new StringReader( uploadString )) ); 

        
		DOMSource source = new DOMSource(doc);
		File file= new File("file.xml");
	    FileWriter writer = new FileWriter(file);
	    StreamResult result = new StreamResult(writer);

	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
		
	    InputStream targetStream = new FileInputStream(file);
	    response.setContentType("text/xml");
	    response.setHeader("Content-Disposition","attachment; filename=file.xml");
	    org.apache.commons.io.IOUtils.copy(targetStream, response.getOutputStream());
	    
	    /*Path path = Paths.get("file.xml");
	    String name = "file.xml";
	    String originalFileName = "file.xml";
	    String contentType = "text/xml";
	    byte[] content = null;
	    try {
	        content = Files.readAllBytes(path);
	    } catch (final IOException e) {
	    }*/
	    
        } catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {  
            e.printStackTrace();  
        } 
	    
	}
	
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public List<Uploads> getAllUploads(){
		
		
		return uploadsDAO.getAll();
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public List<VehicleDetails> getUploadDetails(@PathVariable("id") long id){
		
		
		return vehicleDetailsDAO.getVehiclesByFileId(id);
	}
	
	
	private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        
        return null;
    }
	
	private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
	
}
