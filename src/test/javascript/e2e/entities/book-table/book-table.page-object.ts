import { element, by, ElementFinder } from 'protractor';

export class BookTableComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-book-table div table .btn-danger'));
    title = element.all(by.css('jhi-book-table div h2#page-heading span')).first();

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

export class BookTableUpdatePage {
    pageTitle = element(by.id('jhi-book-table-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    isAvaliableInput = element(by.id('field_isAvaliable'));
    priceInput = element(by.id('field_price'));
    imageUrlInput = element(by.id('field_imageUrl'));
    restaurantSelect = element(by.id('field_restaurant'));
    tableTypeSelect = element(by.id('field_tableType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    getIsAvaliableInput() {
        return this.isAvaliableInput;
    }
    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setImageUrlInput(imageUrl) {
        await this.imageUrlInput.sendKeys(imageUrl);
    }

    async getImageUrlInput() {
        return this.imageUrlInput.getAttribute('value');
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

    async tableTypeSelectLastOption() {
        await this.tableTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async tableTypeSelectOption(option) {
        await this.tableTypeSelect.sendKeys(option);
    }

    getTableTypeSelect(): ElementFinder {
        return this.tableTypeSelect;
    }

    async getTableTypeSelectedOption() {
        return this.tableTypeSelect.element(by.css('option:checked')).getText();
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

export class BookTableDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-bookTable-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-bookTable'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
