package org.shop;

import org.shop.api.ItemService;
import org.shop.api.ProductService;
import org.shop.api.ProposalService;
import org.shop.api.UserService;
import org.shop.api.impl.ProductServiceImpl;
import org.shop.api.impl.ProposalServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * The ShopLauncher class.
 */
public class ShopLauncher {
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	ApplicationContext context =
    			new ClassPathXmlApplicationContext(
    			new String[] {"xml-context-config.xml"});
    	//Get bean by name
    	ItemService itemService = (ItemService) context.getBean("itemService");
    	//Get bean by type
    	ProductService productService = context.getBean(ProductServiceImpl.class);
    	//Get bean by name and type
    	ProposalService proposalService = context.getBean("proposalService", ProposalServiceImpl.class);
    	UserService userService = (UserService) context.getBean("userService_alias");
    	//Check beans
    	System.out.println(itemService.getClass().getSimpleName());
    	System.out.println(productService.getClass().getSimpleName());
    	System.out.println(proposalService.getClass().getSimpleName());
    	System.out.println(userService.getClass().getSimpleName());
    }
}
