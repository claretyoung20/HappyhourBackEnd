import { element, by, ElementFinder } from 'protractor';

export class CustomerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-customer div table .btn-danger'));
    title = element.all(by.css('jhi-customer div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CustomerUpdatePage {
    pageTitle = element(by.id('jhi-customer-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    addressInput = element(by.id('field_address'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    dateUpdatedInput = element(by.id('field_dateUpdated'));
    dateOfBirthInput = element(by.id('field_dateOfBirth'));
    emailInput = element(by.id('field_email'));
    firstNameInput = element(by.id('field_firstName'));
    imgUrlInput = element(by.id('field_imgUrl'));
    lastNameInput = element(by.id('field_lastName'));
    passwordInput = element(by.id('field_password'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    resetDateInput = element(by.id('field_resetDate'));
    reset_KeyInput = element(by.id('field_reset_Key'));
    statusInput = element(by.id('field_status'));
    restaurantSelect = element(by.id('field_restaurant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    async setDateCreatedInput(dateCreated) {
        await this.dateCreatedInput.sendKeys(dateCreated);
    }

    async getDateCreatedInput() {
        return this.dateCreatedInput.getAttribute('value');
    }

    async setDateUpdatedInput(dateUpdated) {
        await this.dateUpdatedInput.sendKeys(dateUpdated);
    }

    async getDateUpdatedInput() {
        return this.dateUpdatedInput.getAttribute('value');
    }

    async setDateOfBirthInput(dateOfBirth) {
        await this.dateOfBirthInput.sendKeys(dateOfBirth);
    }

    async getDateOfBirthInput() {
        return this.dateOfBirthInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setImgUrlInput(imgUrl) {
        await this.imgUrlInput.sendKeys(imgUrl);
    }

    async getImgUrlInput() {
        return this.imgUrlInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async setPasswordInput(password) {
        await this.passwordInput.sendKeys(password);
    }

    async getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setResetDateInput(resetDate) {
        await this.resetDateInput.sendKeys(resetDate);
    }

    async getResetDateInput() {
        return this.resetDateInput.getAttribute('value');
    }

    async setReset_KeyInput(reset_Key) {
        await this.reset_KeyInput.sendKeys(reset_Key);
    }

    async getReset_KeyInput() {
        return this.reset_KeyInput.getAttribute('value');
    }

    getStatusInput() {
        return this.statusInput;
    }

    async restaurantSelectLastOption() {
        await this.restaurantSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async restaurantSelectOption(option) {
        await this.restaurantSelect.sendKeys(option);
    }

    getRestaurantSelect(): ElementFinder {
        return this.restaurantSelect;
    }

    async getRestaurantSelectedOption() {
        return this.restaurantSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CustomerDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-customer-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-customer'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
