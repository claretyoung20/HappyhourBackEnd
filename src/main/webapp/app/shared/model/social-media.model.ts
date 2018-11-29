export interface ISocialMedia {
    id?: number;
    name?: string;
    link?: string;
    restaurantId?: number;
}

export class SocialMedia implements ISocialMedia {
    constructor(public id?: number, public name?: string, public link?: string, public restaurantId?: number) {}
}
