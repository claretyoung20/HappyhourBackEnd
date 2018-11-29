import { element, by, ElementFinder } from 'protractor';

export class CouponComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-coupon div table .btn-danger'));
    title = element.all(by.css('jhi-coupon div h2#page-heading span')).first();

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

export class CouponUpdatePage {
    pageTitle = element(by.id('jhi-coupon-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    dateCreatedInput = element(by.id('field_dateCreated'));
    dateUpdatedInput = element(by.id('field_dateUpdated'));
    endDateInput = element(by.id('field_endDate'));
    isActiveInput = element(by.id('field_isActive'));
    noPerUserInput = element(by.id('field_noPerUser'));
    priceInput = element(by.id('field_price'));
    startFromDateInput = element(by.id('field_startFromDate'));
    maxAmountToApplyInput = element(by.id('field_maxAmountToApply'));
    restaurantSelect = element(by.id('field_restaurant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
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

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
    }

    getIsActiveInput() {
        return this.isActiveInput;
    }
    async setNoPerUserInput(noPerUser) {
        await this.noPerUserInput.sendKeys(noPerUser);
    }

    async getNoPerUserInput() {
        return this.noPerUserInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setStartFromDateInput(startFromDate) {
        await this.startFromDateInput.sendKeys(startFromDate);
    }

    async getStartFromDateInput() {
        return this.startFromDateInput.getAttribute('value');
    }

    async setMaxAmountToApplyInput(maxAmountToApply) {
        await this.maxAmountToApplyInput.sendKeys(maxAmountToApply);
    }

    async getMaxAmountToApplyInput() {
        return this.maxAmountToApplyInput.getAttribute('value');
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

export class CouponDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-coupon-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-coupon'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
