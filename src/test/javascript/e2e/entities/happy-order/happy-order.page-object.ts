import { element, by, ElementFinder } from 'protractor';

export class HappyOrderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-happy-order div table .btn-danger'));
    title = element.all(by.css('jhi-happy-order div h2#page-heading span')).first();

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

export class HappyOrderUpdatePage {
    pageTitle = element(by.id('jhi-happy-order-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    baseTotalInput = element(by.id('field_baseTotal'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    dateUpdatedInput = element(by.id('field_dateUpdated'));
    totalPriceInput = element(by.id('field_totalPrice'));
    orderStatusSelect = element(by.id('field_orderStatus'));
    customerSelect = element(by.id('field_customer'));
    couponSelect = element(by.id('field_coupon'));
    restaurantSelect = element(by.id('field_restaurant'));
    staffSelect = element(by.id('field_staff'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setBaseTotalInput(baseTotal) {
        await this.baseTotalInput.sendKeys(baseTotal);
    }

    async getBaseTotalInput() {
        return this.baseTotalInput.getAttribute('value');
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

    async setTotalPriceInput(totalPrice) {
        await this.totalPriceInput.sendKeys(totalPrice);
    }

    async getTotalPriceInput() {
        return this.totalPriceInput.getAttribute('value');
    }

    async orderStatusSelectLastOption() {
        await this.orderStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async orderStatusSelectOption(option) {
        await this.orderStatusSelect.sendKeys(option);
    }

    getOrderStatusSelect(): ElementFinder {
        return this.orderStatusSelect;
    }

    async getOrderStatusSelectedOption() {
        return this.orderStatusSelect.element(by.css('option:checked')).getText();
    }

    async customerSelectLastOption() {
        await this.customerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async customerSelectOption(option) {
        await this.customerSelect.sendKeys(option);
    }

    getCustomerSelect(): ElementFinder {
        return this.customerSelect;
    }

    async getCustomerSelectedOption() {
        return this.customerSelect.element(by.css('option:checked')).getText();
    }

    async couponSelectLastOption() {
        await this.couponSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async couponSelectOption(option) {
        await this.couponSelect.sendKeys(option);
    }

    getCouponSelect(): ElementFinder {
        return this.couponSelect;
    }

    async getCouponSelectedOption() {
        return this.couponSelect.element(by.css('option:checked')).getText();
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

    async staffSelectLastOption() {
        await this.staffSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async staffSelectOption(option) {
        await this.staffSelect.sendKeys(option);
    }

    getStaffSelect(): ElementFinder {
        return this.staffSelect;
    }

    async getStaffSelectedOption() {
        return this.staffSelect.element(by.css('option:checked')).getText();
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

export class HappyOrderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-happyOrder-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-happyOrder'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
