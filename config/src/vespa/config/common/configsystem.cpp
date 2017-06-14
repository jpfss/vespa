// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.

#include "configsystem.h"
#include <vespa/defaults.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

namespace config {

namespace {

vespalib::string getConfigProxyFileName() {
    vespalib::string root(vespa::Defaults::vespaHome());
    return root + "var/run/configproxy.pid";
}

}

ConfigSystem::ConfigSystem() :
    _configProxyPidFile(getConfigProxyFileName())
{
}

bool ConfigSystem::isUp() const {
    return isConfigProxyRunning();
}

bool ConfigSystem::isConfigProxyRunning() const {
    struct stat fs;

    int retval = stat(_configProxyPidFile.c_str(), &fs);
    if (retval == 0) {
        if (S_ISREG(fs.st_mode)) {
            return true;
        }
    }
    return false;
}

}
