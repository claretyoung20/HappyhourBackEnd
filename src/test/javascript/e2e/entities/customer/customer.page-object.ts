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
    phoneNumberInput = element(by.id('field_phoneNumber'));
    statusInput = element(by.id('field_status'));
    restaurantSelect = element(by.id('field_restaurant'));
    userSelect = element(by.id('field_user'));

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

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
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

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
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
