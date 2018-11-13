import { element, by, ElementFinder } from 'protractor';

export class ProductTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product-type div table .btn-danger'));
    title = element.all(by.css('jhi-product-type div h2#page-heading span')).first();

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

export class ProductTypeUpdatePage {
    pageTitle = element(by.id('jhi-product-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    dateUpdatedInput = element(by.id('field_dateUpdated'));
    typeInput = element(by.id('field_type'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
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

    async setTypeInput(type) {
        await this.typeInput.sendKeys(type);
    }

    async getTypeInput() {
        return this.typeInput.getAttribute('value');
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

export class ProductTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-productType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-productType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
