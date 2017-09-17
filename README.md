# spring-auto-controller

Expose your service methods as endpoints.

# How to use

Configure AutoController importing configuration class (use this on any of your configuration classes):
```
@Import(AutoControllerConfig.class)
```

Annotate methods that you want to be exposed.
```
@Service
public class TestService {

    @Expose
    public String greet(String name) {
        return "Hello " + name;
    }

}
```
In this case, the mapping will be '/test/greet'.

**Convention**:

* 'Service' part of class name is omitted
* 'get' part of method name is omitted
* RequestMethod is GET unless there is a parameter annotated with @RequestBody, then it's POST
* @Expose contains @ResponseBody, so the result is JSON

# Download

[![](https://jitpack.io/v/mgrzeszczak/spring-auto-controller.svg)](https://jitpack.io/#mgrzeszczak/spring-auto-controller)

# License
```
MIT License

Copyright (c) 2017 Maciej Grzeszczak

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
