import { element, by, ElementFinder } from 'protractor';

export class TimeManagerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-time-manager div table .btn-danger'));
    title = element.all(by.css('jhi-time-manager div h2#page-heading span')).first();

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

export class TimeManagerUpdatePage {
    pageTitle = element(by.id('jhi-time-manager-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dayInput = element(by.id('field_day'));
    startTimeInput = element(by.id('field_startTime'));
    endTimeInput = element(by.id('field_endTime'));
    restaurantSelect = element(by.id('field_restaurant'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDayInput(day) {
        await this.dayInput.sendKeys(day);
    }

    async getDayInput() {
        return this.dayInput.getAttribute('value');
    }

    async setStartTimeInput(startTime) {
        await this.startTimeInput.sendKeys(startTime);
    }

    async getStartTimeInput() {
        return this.startTimeInput.getAttribute('value');
    }

    async setEndTimeInput(endTime) {
        await this.endTimeInput.sendKeys(endTime);
    }

    async getEndTimeInput() {
        return this.endTimeInput.getAttribute('value');
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

export class TimeManagerDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-timeManager-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-timeManager'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
