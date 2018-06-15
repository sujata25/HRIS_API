
customersetup

=======================================================
add_new_category_btn		id		main_content_ctl00_ctlCategory_lnkAddCategory
add_category				xpath	//*[text()='Add Category']
code						id		main_content_ctl00_txtCode
name						id		main_content_ctl00_txtName
individual_checkbox			xpath	//label[contains(text(),'${labelName}')]/parent::span/following-sibling::div//input
check1                       id       main_content_ctl00_chkIsIndividualCategory
save_btn					id		main_content_ctl00_ButtonsPlaceHolder_btnSaveAndClose
new_row						xpath	//td[${value1}]/span[text()='${value2}']
table						xpath	//table[@class = 'table table-striped table-bordered table-hover']/tbody/tr
frame_1                       xpath   //div[@class='modal-body']/iframe
delete_btn					xpath		//input[@value = 'Delete']
delete_confirmation_ok			xpath	//button[text() = 'OK']
check_tick					xpath		//table[@class = 'table table-striped table-bordered table-hover']//tr//td[1]/span[text()='${code_value}']/parent::td/following-sibling::td[${category_column}]//i
edit_btn				xpath		//table[@class = '${table_name}']//tr//td[1]/span[text()='${code_value}']/parent::td/following-sibling::td[6]/a