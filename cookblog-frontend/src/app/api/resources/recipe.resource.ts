import { CategoryResource } from './category.resource';
import { CommentResource } from './comment.resource';
import { IngredientResource } from './ingredient.resource';
import { ImageResource } from './image.resource';

export class RecipeResource {
  private readonly _id: number;
  private readonly _title: string;
  private readonly _description: string;
  private readonly _ingredients: IngredientResource[];
  private readonly _instructions: string;
  private readonly _image: ImageResource;
  private readonly _comments: CommentResource[];
  private readonly _category: CategoryResource;
  private readonly _preparationTimeInMinutes: number | null;
  private readonly _portions: number | null;
  private readonly _calorificValue: number | null;

  constructor(
    id: number,
    title: string,
    description: string,
    ingredients: IngredientResource[],
    instructions: string,
    image: ImageResource,
    comments: CommentResource[],
    category: CategoryResource,
    preparationTimeInMinutes: number | null,
    portions: number | null,
    calorificValue: number | null,
  ) {
    this._id = id;
    this._title = title;
    this._description = description;
    this._ingredients = ingredients;
    this._instructions = instructions;
    this._image = image;
    this._comments = comments;
    this._category = category;
    this._preparationTimeInMinutes = preparationTimeInMinutes;
    this._portions = portions;
    this._calorificValue = calorificValue;
  }

  get id() {
    return this._id;
  }

  get title() {
    return this._title;
  }

  get description() {
    return this._description;
  }

  get ingredients() {
    return this._ingredients;
  }

  get instructions() {
    return this._instructions;
  }

  get image() {
    return this._image;
  }

  get comments() {
    return this._comments;
  }

  get category() {
    return this._category;
  }

  get preparationTimeInMinutes() {
    return this._preparationTimeInMinutes;
  }

  get portions() {
    return this._portions;
  }

  get calorificValue() {
    return this._calorificValue;
  }
}
