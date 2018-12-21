/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BookTableComponentsPage, BookTableDeleteDialog, BookTableUpdatePage } from './book-table.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('BookTable e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let bookTableUpdatePage: BookTableUpdatePage;
    let bookTableComponentsPage: BookTableComponentsPage;
    /*let bookTableDeleteDialog: BookTableDeleteDialog;*/
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load BookTables', async () => {
        await navBarPage.goToEntity('book-table');
        bookTableComponentsPage = new BookTableComponentsPage();
        expect(await bookTableComponentsPage.getTitle()).to.eq('happybourBackEndApp.bookTable.home.title');
    });

    it('should load create BookTable page', async () => {
        await bookTableComponentsPage.clickOnCreateButton();
        bookTableUpdatePage = new BookTableUpdatePage();
        expect(await bookTableUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.bookTable.home.createOrEditLabel');
        await bookTableUpdatePage.cancel();
    });

    /* it('should create and save BookTables', async () => {
        const nbButtonsBeforeCreate = await bookTableComponentsPage.countDeleteButtons();

        await bookTableComponentsPage.clickOnCreateButton();
        const selectedIsAvaliable = bookTableUpdatePage.getIsAvaliableInput();
        if (await selectedIsAvaliable.isSelected()) {
            await bookTableUpdatePage.getIsAvaliableInput().click();
            expect(await bookTableUpdatePage.getIsAvaliableInput().isSelected()).to.be.false;
        } else {
            await bookTableUpdatePage.getIsAvaliableInput().click();
            expect(await bookTableUpdatePage.getIsAvaliableInput().isSelected()).to.be.true;
        }
        await bookTableUpdatePage.setPriceInput('5');
        expect(await bookTableUpdatePage.getPriceInput()).to.eq('5');
        await bookTableUpdatePage.setImageUrlInput('imageUrl');
        expect(await bookTableUpdatePage.getImageUrlInput()).to.eq('imageUrl');
        await bookTableUpdatePage.setTable_imageInput(absolutePath);
        await bookTableUpdatePage.restaurantSelectLastOption();
        await bookTableUpdatePage.tableTypeSelectLastOption();
        await bookTableUpdatePage.save();
        expect(await bookTableUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await bookTableComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });*/

    /* it('should delete last BookTable', async () => {
        const nbButtonsBeforeDelete = await bookTableComponentsPage.countDeleteButtons();
        await bookTableComponentsPage.clickOnLastDeleteButton();

        bookTableDeleteDialog = new BookTableDeleteDialog();
        expect(await bookTableDeleteDialog.getDialogTitle())
            .to.eq('happybourBackEndApp.bookTable.delete.question');
        await bookTableDeleteDialog.clickOnConfirmButton();

        expect(await bookTableComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
