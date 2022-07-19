DELETE FROM public.recipe_ingredient;
DELETE FROM public.recipe;
DELETE FROM public.ingredient;

INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(1, 1, 'Salt');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(2, 7, 'Egg');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(3, 2, 'Chicken');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(4, 1, 'Sugar');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(5, 0, 'Potato');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(6, 4, 'Apple');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(7, 2, 'Ribs');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(8, 4, 'Pineapple');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(9, 7, 'Water');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(10, 6, 'Rice');
INSERT INTO public.ingredient (id, ingredient_type, "name") VALUES(11, 0, 'Mushroom');
