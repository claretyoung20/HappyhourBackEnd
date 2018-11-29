/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SocialMediaComponentsPage, SocialMediaDeleteDialog, SocialMediaUpdatePage } from './social-media.page-object';

const expect = chai.expect;

describe('SocialMedia e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let socialMediaUpdatePage: SocialMediaUpdatePage;
    let socialMediaComponentsPage: SocialMediaComponentsPage;
    let socialMediaDeleteDialog: SocialMediaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SocialMedias', async () => {
        await navBarPage.goToEntity('social-media');
        socialMediaComponentsPage = new SocialMediaComponentsPage();
        expect(await socialMediaComponentsPage.getTitle()).to.eq('happybourBackEndApp.socialMedia.home.title');
    });

    it('should load create SocialMedia page', async () => {
        await socialMediaComponentsPage.clickOnCreateButton();
        socialMediaUpdatePage = new SocialMediaUpdatePage();
        expect(await socialMediaUpdatePage.getPageTitle()).to.eq('happybourBackEndApp.socialMedia.home.createOrEditLabel');
        await socialMediaUpdatePage.cancel();
    });

    it('should create and save SocialMedias', async () => {
        const nbButtonsBeforeCreate = await socialMediaComponentsPage.countDeleteButtons();

        await socialMediaComponentsPage.clickOnCreateButton();
        await socialMediaUpdatePage.setNameInput('name');
        expect(await socialMediaUpdatePage.getNameInput()).to.eq('name');
        await socialMediaUpdatePage.setLinkInput('link');
        expect(await socialMediaUpdatePage.getLinkInput()).to.eq('link');
        await socialMediaUpdatePage.restaurantSelectLastOption();
        await socialMediaUpdatePage.save();
        expect(await socialMediaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await socialMediaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SocialMedia', async () => {
        const nbButtonsBeforeDelete = await socialMediaComponentsPage.countDeleteButtons();
        await socialMediaComponentsPage.clickOnLastDeleteButton();

        socialMediaDeleteDialog = new SocialMediaDeleteDialog();
        expect(await socialMediaDeleteDialog.getDialogTitle()).to.eq('happybourBackEndApp.socialMedia.delete.question');
        await socialMediaDeleteDialog.clickOnConfirmButton();

        expect(await socialMediaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
