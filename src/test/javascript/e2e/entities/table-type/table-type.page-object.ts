import { element, by, ElementFinder } from 'protractor';

export class TableTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-table-type div table .btn-danger'));
    title = element.all(by.css('jhi-table-type div h2#page-heading span')).first();

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

export class TableTypeUpdatePage {
    pageTitle = element(by.id('jhi-table-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    descriptionInput = element(by.id('field_description'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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

export class TableTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-tableType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-tableType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
