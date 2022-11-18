<center> 

# **Inventory.io**

</center>

## Inventory.io is a quick and efficient program designed for small retailers to manage their in-store offline inventories. Some features planned for this program include, but are not limited to:
	
- **Ability** to create categories for all the products a retailer has to offer; within these categories, retailers can also add additional sub-categories—allowing them to carry products that meet specific needs.
 Example: Let's say a retailer—who is focusing on computer parts—is selling CPUs, RAM modules, SSDs, and GPUs. Using Inventory.io, this retailer can create specific categories of type, CPU, RAM, SSD, or GPU. The retailer can now add all matching products into said categories, allowing them to separate offerings by their purpose; If needed, they can add additional sub-categories that further distinguish the products by their respective brand offerings.

<space>

- **Ability** to add descriptors for all the products offered, making it relatively easy for employees to add/remove/modify/check a product. Default descriptors include pictures of individual products, information about current stock remaining and where in the store to find it, the current price of a product, the color offerings of a product (if applicable), and product number. (*Additional descriptors can be added using the application*)

<space>

- **Ability** to create unique product IDs for every product presented by the retailer; this makes it easy to track an item down.

<space>

- **Ability** to add/subtract the "x" number of products from stock if the customer refunds/buys it, either by inputting the product ID and "x" number refunded/purchased or allowing a scanner to autofill data. (Scanner autofill is an idea I want to explore, but not my main focus)

## Why am I interesting in making Inventory.io? 
Having a friend whose parents recently started a local business, I have gotten first-hand experience with the challenges of managing in-store inventory. Now, you may wonder, "how does something seemingly so easy, end up being so onerous?" Well, consider this: As a retailer, if you have no idea what is currently present in your inventory, how are you supposed to know if a product is available for sale? 
Arguably, you could make manual adjustments to your inventory counts; however, a single error could result in the mismanagement of stock information—forcing you to call a manual inventory check-up, which results in wasted time—in a market where time is money. Sure, you could buy a commercialized solution to this otherwise local problem, but that most certainly does not come cheap. This cost may be somewhat justified considering most of these commercial offerings are targeted toward big retailers who own multiple stores.
However, as I mentioned earlier, the business owned by my friend's parents—as of right now—is anything but big; these people only own one store and, as such, only require the management of local/in-store inventory. Considering what I've said, wouldn't it make more financial sense for them to go with a cheaper alternative, an alternative such as Inventory.io? Of course it would, and that is my main interest behind creating Inventory.io in the first place, an app made for small retailers to manage their in-store inventory without any headaches and, most importantly, without taking a chunk of their profits.
## User Stories

- As a user, I want to be able to create a category CPU, and within this, add multiple sub-categories for the different brands of CPUs. 
<pre>
Example: (CPU)┐
              ├>(Intel)	
              └>(AMD)
</pre>	
- As a user, I want to be able to create a sub-category within a sub-category. 
<pre>
Example: (CPU)┐             
              └>(INTEL)┐	
                       └>(i7)
													
</pre>

- As a user, I want to be able to add small descriptions for all my products.

- As a user, I want to be able to modify current product information and delete categories or products.

- As a user, I want to be able to save my inventory.

- As a user, I want to be able to reload a saved inventory.

***Side note: If time permits, I want to create the ability to sync inventory data online in the most cost-effective way. If a retailer has multiple stores, this online data will allow them to check/sync stock information with every store. Additionally, if the retailer has an online website, this data could also be shared with customers, allowing them to check online if a product is in stock and, if so, where it might be located.***

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by left-clicking and choosing the "InventoryGUI" Object on the top left; once it is highlighted in blue, you can then right-click on it and choose "Add New Main Inventory Item." If the previous steps are followed correctly, you will be greeted by a new pop-up menu. 
	For the sake of simplicity: 
	<pre>
	- Fill in the field "Name of inventory Object?" with "test"
	- Fill in "Description for Inventory Object?" with "testing test" 
	- Leave the little box after "Is this inventory Object a product?" unchecked. 
	- Fill in "What's the color for this Inventory Object? (N/A if not applicable)" with "N/A"
	- Click the "OK" button found at the bottom.
	</pre>
	
	<space> 
	
	You will now see that a new object called "\*test" as been created inside "InventoryGUI," you have now successfully added an object of class X inside Y class! You can re-do the previous steps multiple times to add multiple Xs in class Y.
	
	
	
- You can generate the second required event related to adding Xs to a Y by left-clicking on the new X object you made earlier; once the object is highlighted in blue, information about it will appear towards the right. Draw your attention the field "NAME:" and double left click on it, you will be greeted by a new pop-up menu where you can enter a new name for this X object. For the sake of simplicity, enter "renamed test" to be the new name for this object and press OK. At this point, you will see a pop-up confirming the change you made, press OK and look at your X object, it should now be renamed to "renamed test."

	<space>

- You can locate my visual component by looking at the Save and Load buttons on the top right, they both are accompanied by images that visually describe what the buttons do. I have also added images for both, the option that let you add Xs into Ys (green "+" circle icon) and the option that removes a selected X from Y (red (-) circle icon). You can view those by highlighting the X object you renamed and right-clicking on it.
- You can save the state of my application by clicking on the "Save" button on the top right, which is just below the "Load" button. Once the application has been saved, you will see a pop-up menu confirming this by saying "Saved." You can now exit the program. Note: Your save is written in the data folder inside this project. 
- You can reload the state of my application by click on the "Load" button on the top right, which is just above the "Save" button. If you have followed all the steps mentioned thus far, you are free to test the load feature by saving and exiting the program. Once the program is closed, you can re-run the program and click on the "Load" button, this should restore the program prior to exiting it.