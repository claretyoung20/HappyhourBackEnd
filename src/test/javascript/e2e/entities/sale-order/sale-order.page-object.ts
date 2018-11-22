import { element, by, ElementFinder } from 'protractor';

export class SaleOrderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-sale-order div table .btn-danger'));
    title = element.all(by.css('jhi-sale-order div h2#page-heading span')).first();

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

export class SaleOrderUpdatePage {
    pageTitle = element(by.id('jhi-sale-order-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    basePriceInput = element(by.id('field_basePrice'));
    discountAmountInput = element(by.id('field_discountAmount'));
    originalPriceInput = element(by.id('field_originalPrice'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    dateUpdatedInput = element(by.id('field_dateUpdated'));
    productSelect = element(by.id('field_product'));
    happyOrderSelect = element(by.id('field_happyOrder'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setBasePriceInput(basePrice) {
        await this.basePriceInput.sendKeys(basePrice);
    }

    async getBasePriceInput() {
        return this.basePriceInput.getAttribute('value');
    }

    async setDiscountAmountInput(discountAmount) {
        await this.discountAmountInput.sendKeys(discountAmount);
    }

    async getDiscountAmountInput() {
        return this.discountAmountInput.getAttribute('value');
    }

    async setOriginalPriceInput(originalPrice) {
        await this.originalPriceInput.sendKeys(originalPrice);
    }

    async getOriginalPriceInput() {
        return this.originalPriceInput.getAttribute('value');
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

    async productSelectLastOption() {
        await this.productSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productSelectOption(option) {
        await this.productSelect.sendKeys(option);
    }

    getProductSelect(): ElementFinder {
        return this.productSelect;
    }

    async getProductSelectedOption() {
        return this.productSelect.element(by.css('option:checked')).getText();
    }

    async happyOrderSelectLastOption() {
        await this.happyOrderSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async happyOrderSelectOption(option) {
        await this.happyOrderSelect.sendKeys(option);
    }

    getHappyOrderSelect(): ElementFinder {
        return this.happyOrderSelect;
    }

    async getHappyOrderSelectedOption() {
        return this.happyOrderSelect.element(by.css('option:checked')).getText();
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

export class SaleOrderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-saleOrder-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-saleOrder'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
