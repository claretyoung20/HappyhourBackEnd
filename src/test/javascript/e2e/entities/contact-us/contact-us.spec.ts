/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactUsComponentsPage, ContactUsDeleteDialog, ContactUsUpdatePage } from './contact-us.page-object';

const expect = chai.expect;

describe('ContactUs e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let contactUsUpdatePage: ContactUsUpdatePage;
    let contactUsComponentsPage: ContactUsComponentsPage;
    let contactUsDeleteDialog: ContactUsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Contactuses', async () => {
        await navBarPage.goToEntity('contact-us');
        contactUsComponentsPage = new ContactUsComponentsPage();
        expect(await contactUsComponentsPage.getTitle()).to.eq('happybourBackEndApp.contactUs.home.title');
    });

    it('should load create ContactUs page', async () => {
        await contactUsComponentsPage.clickOnCreateButton();
        contactUsUpdatePage = new ContactUsUpdatePage();
        expect(await contactUsUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.contactUs.home.createOrEditLabel');
        await contactUsUpdatePage.cancel();
    });

    it('should create and save Contactuses', async () => {
        const nbButtonsBeforeCreate = await contactUsComponentsPage.countDeleteButtons();

        await contactUsComponentsPage.clickOnCreateButton();
        await contactUsUpdatePage.setFullNameInput('fullName');
        expect(await contactUsUpdatePage.getFullNameInput()).to.eq('fullName');
        await contactUsUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await contactUsUpdatePage.getDateCreatedInput()).to.contain('2001-01-01T02:30');
        await contactUsUpdatePage.setEmailInput('email');
        expect(await contactUsUpdatePage.getEmailInput()).to.eq('email');
        await contactUsUpdatePage.setPhoneNumberInput('phoneNumber');
        expect(await contactUsUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        await contactUsUpdatePage.setCommentInput('comment');
        expect(await contactUsUpdatePage.getCommentInput()).to.eq('comment');
        await contactUsUpdatePage.restaurantSelectLastOption();
        await contactUsUpdatePage.save();
        expect(await contactUsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await contactUsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ContactUs', async () => {
        const nbButtonsBeforeDelete = await contactUsComponentsPage.countDeleteButtons();
        await contactUsComponentsPage.clickOnLastDeleteButton();

        contactUsDeleteDialog = new ContactUsDeleteDialog();
        expect(await contactUsDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.contactUs.delete.question');
        await contactUsDeleteDialog.clickOnConfirmButton();

        expect(await contactUsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
