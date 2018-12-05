import { element, by, ElementFinder } from 'protractor';

export class ReservationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-reservation div table .btn-danger'));
    title = element.all(by.css('jhi-reservation div h2#page-heading span')).first();

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

export class ReservationUpdatePage {
    pageTitle = element(by.id('jhi-reservation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    commentInput = element(by.id('field_comment'));
    statusInput = element(by.id('field_status'));
    reserverDateInput = element(by.id('field_reserverDate'));
    updatedDateInput = element(by.id('field_updatedDate'));
    periodInput = element(by.id('field_period'));
    staffSelect = element(by.id('field_staff'));
    bookTableSelect = element(by.id('field_bookTable'));
    customerSelect = element(by.id('field_customer'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCommentInput(comment) {
        await this.commentInput.sendKeys(comment);
    }

    async getCommentInput() {
        return this.commentInput.getAttribute('value');
    }

    async setStatusInput(status) {
        await this.statusInput.sendKeys(status);
    }

    async getStatusInput() {
        return this.statusInput.getAttribute('value');
    }

    async setReserverDateInput(reserverDate) {
        await this.reserverDateInput.sendKeys(reserverDate);
    }

    async getReserverDateInput() {
        return this.reserverDateInput.getAttribute('value');
    }

    async setUpdatedDateInput(updatedDate) {
        await this.updatedDateInput.sendKeys(updatedDate);
    }

    async getUpdatedDateInput() {
        return this.updatedDateInput.getAttribute('value');
    }

    async setPeriodInput(period) {
        await this.periodInput.sendKeys(period);
    }

    async getPeriodInput() {
        return this.periodInput.getAttribute('value');
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

    async bookTableSelectLastOption() {
        await this.bookTableSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async bookTableSelectOption(option) {
        await this.bookTableSelect.sendKeys(option);
    }

    getBookTableSelect(): ElementFinder {
        return this.bookTableSelect;
    }

    async getBookTableSelectedOption() {
        return this.bookTableSelect.element(by.css('option:checked')).getText();
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

export class ReservationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-reservation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-reservation'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
