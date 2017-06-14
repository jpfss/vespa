// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
/**
 * \class storage::framework::MetricRegistrator
 * \ingroup metric
 *
 * \brief Interface used to register a metric in the backend.
 *
 * To avoid needing the framework module to depend on the metric system (in
 * case any users don't use it), this class exist to remove this dependency.
 */
#pragma once

#include <vespa/vespalib/stllike/string.h>
#include <vespa/vespalib/util/sync.h>

namespace storage {
namespace framework {

struct MetricRegistrator {
    virtual ~MetricRegistrator() {}

    virtual void registerMetric(metrics::Metric&) = 0;
    virtual void registerUpdateHook(vespalib::stringref name,
                                    MetricUpdateHook& hook,
                                    SecondTime period) = 0;
    virtual vespalib::MonitorGuard getMetricManagerLock() = 0;
};

} // framework
} // storage

