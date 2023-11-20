import { RecipeResource } from './recipe.resource';

export class CategoryResource {
  private readonly _id: number | null;
  private readonly _name: string;
  private readonly _recipes: RecipeResource[] | null;

  constructor(
    id: number | null,
    name: string,
    recipes: RecipeResource[] | null,
  ) {
    this._id = id;
    this._name = name;
    this._recipes = recipes;
  }

  get id() {
    return this._id;
  }

  get name() {
    return this._name;
  }

  get recipes() {
    return this._recipes;
  }
}
