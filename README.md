# Tubar

> Damn, I wish I knew what bar to go to next.
> *Me - 11PM on a saturday night*

## Quickstart

### Requirements

  * Maven
  * Java 7
  * A good bash

### Download and test the app

  * `git clone` this repo.
  * `mvn test`

At this point, you should see a message (or many messages) that says something like the following:

````
WARNING - the env variable GOOGLE_DISTANCE_MATRIX_KEY is not set. Set this variable to avoid OVER_QUERY_LIMIT status codes when querying the Google Maps Distance Matrix API
````

To get rid of this (and have your own query limit), you need to export your Google Maps Distance Matrix API key as an environment variable. **This is not required to test the app.**

  * `mvn appengine:devserver`

And you should be up and running.

### Notes

At the moment, the web client doesn't do ~~anything~~ much. Remember that this is work in progress, and the current focus is the algorithm that makes the whole thing work.

## If you feel like reading more

Over at [EISTI](www.eisti.fr), we like beer. We also like to walk as little as possible to get to our beer. But above all, we like creating and implementing algorithms.

So here's the deal: how can we combine these three things to create something cool?

The answer lies in **Tubar**. Tubar isn't just an awesome sounding code name, it is a word built by compressing "tournée" and "bar" together.

What Tubar does is it tells you and your group of friends the best order in which you should visit the multiple bars that you like. 

It is built on the following things:

  * A Google App Engine Cloud Endpoints application that makes all kinds of fancy calculations based on the current time, the distance between the different bars and the position of the planets in the solar system.
  * And that's all for the moment.

## Algorithm

The application calculates a possible solution the following way:

  1. Given a series of bars that are to be visited, calculate the distance (we will call it `d_origin_destination_0`) between all the bars using the [Google Maps API](https://developers.google.com/maps/documentation/distancematrix/). We consider that the users will walk between every bar.
  1. Calculate an initial `Solution` object using the [Nearest Neighbour](http://en.wikipedia.org/wiki/Nearest_neighbour_algorithm) method.
  1. Use a [Simulated Annealing](http://en.wikipedia.org/wiki/Simulated_annealing) algorithm to generate a better solution. The parameters for the alogrithm (initial temperature, cooling factor, number of iterations per temperature, probability of accepting a non improving solution) are empirical and will change depending on the number of `Bar` inputs, maximum distance between them, ...

## Roadmap

Here's what we may want to develop in the near future (in no particular order):
  * Data persistence: upon calculating a new solution, saving the input parameters might enable us to better appreciate the values of the SA parameters.
  * Algorithm variants: 
    * [Neural Network](http://en.wikipedia.org/wiki/Artificial_neural_network).
    * [Complex stuff using Genetic Clustering to make the SA faster](http://dos.iitm.ac.in/LabPapers/parallelSAJPDC.pdf).

## Reference

  * [Comparison of TSP Algorithms](http://bardzo.be/0sem/NAI/rozne/Comparison%20of%20TSP%20Algorithms/Comparison%20of%20TSP%20Algorithms.PDF), *Byung-In Kim, Jae-Ik Shim, Min Zhang*
