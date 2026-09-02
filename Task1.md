# Task 1

> This is the original exercise specification. It has been completed and the repository has subsequently been upgraded into a runnable Spring Boot REST example. The application now uses real Spring MVC annotations; see `README.md`.

1. Fill content of the method ProductService.getAllProductNames() that should return all product names using ProductRepository.findAll().
To test it, please run unit test ProductServiceTest.getAllProductNames_correctNamesReturned() - it must run with success

2. Fill the method ProductService.saveOrUpdateAll(List<ProductEntity> productEntities) to save or update all provided products in parameter, use ProductRepository.saveOrUpdate().
In next step write unit test ProductServiceTest.saveOrUpdateAll() to check your implementation is working correct

3. Create controller ProductController to provide REST API for:

- find all products
- save or update Products (list)
- delete product by id
