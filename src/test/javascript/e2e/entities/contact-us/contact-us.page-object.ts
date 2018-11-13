import { element, by, ElementFinder } from 'protractor';

export class ContactUsComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-contact-us div table .btn-danger'));
    title = element.all(by.css('jhi-contact-us div h2#page-heading span')).first();

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

export class ContactUsUpdatePage {
    pageTitle = element(by.id('jhi-contact-us-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    fullNameInput = element(by.id('field_fullName'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    emailInput = element(by.id('field_email'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    commentInput = element(by.id('field_comment'));
    restaurantSelect = element(by.id('field_restaurant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setFullNameInput(fullName) {
        await this.fullNameInput.sendKeys(fullName);
    }

    async getFullNameInput() {
        return this.fullNameInput.getAttribute('value');
    }

    async setDateCreatedInput(dateCreated) {
        await this.dateCreatedInput.sendKeys(dateCreated);
    }

    async getDateCreatedInput() {
        return this.dateCreatedInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setCommentInput(comment) {
        await this.commentInput.sendKeys(comment);
    }

    async getCommentInput() {
        return this.commentInput.getAttribute('value');
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

export class ContactUsDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-contactUs-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-contactUs'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
