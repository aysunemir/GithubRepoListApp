## Overview

Github Repo List App, lists github repositories by searching username. Navigates to repository detail page 
by clicking the list item. In detail page selected repository can be added to (or removed from) favorites.

## Details

In listing page, user types a username in input area and fetches repositories when clicks button.
When empty list is returned, then an info text about not founding any repository is shown.
When network is not connected or an error is returned from service, then a snackBar with appropriate text is shown.
When a non-empty list is returned, response items are compared with the local favorite list items by ids. If item 
has been added to favorites then favorite is set true to show star icon on the ui list item next to repository name.
In repository detail page, repository owner name and avatar are shown with repository star count and open issue count.
Repository name and favorite info are shown in toolbar. If repository is favorite then favorite button on toolbar 
is set as favorite otherwise set as non favorite.
When user clicks button, if repository is favorite then removes it from db otherwise adds it to db and updates button icon.

## Tech Stack

MVVM Pattern
Kotlin based
Minimum SDK level 21

-LiveData - observable data holder
-ViewModel - presentation layer keeps ui related logic
-Navigation - for managing fragment operations
-Room - database
-DataBinding - for binding data in layouts
-Hilt - provides dependency injection
-Coroutines - for asynchronous operations
-Retrofit - API request / response
-Glide - for image loading

-Truth - for assertions in tests
-Mockito - for mocking test objects
-Hilt Testing - for testing dependency injection and room objects
-Coroutines Test - for testing suspend functions and flows

## App Architecture

Single Activity architecture

Mappers - converts object to another object
LocalDataSource - provides data from database for repository
RemoteDataSource - provides data from remote service for repository
Repository - handles data operations, provides data for presentation layer
UseCases - for business rules
ViewModel - provides data for ui, each fragment has its own viewModel
Activity/Fragment - ui layer

## Test

Tested mappers, repository, database operations and viewModels

